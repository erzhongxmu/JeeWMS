import { Global, route as mergeRoute } from '../helpers/config';
import { copyObject, parseQuery } from '../helpers/util';
import { err } from '../helpers/warn';
import { baiduApple, touTiao } from '../helpers/compile';
/**
 * 触发指定生命钩子
 * @param {Array} funList	//需要执行的方法列表
 * @param {Object} args //触发生命钩子传递的参数
 */
export const callAppHook = function (funList, args) {
    for (let i = 0; i < funList.length; i += 1) {
        funList[i].call(this, args);
    }
};
/**
 * @param {Object} page //当前顶级页面对象
 * @param {Object} vim:? //是否获取 $vm 对象还是 $mp 对象
 */
export const getPageVmOrMp = function (page, vim = true) {
    if (vim) {
        return page.$vm;
    }
    const { $mp } = page.$vm;
    baiduApple(() => {	// 百度小程序新增一个route属性
        $mp.page.route = $mp.page.is;
    });
    touTiao(() => {	// 头条小程序新增一个route属性
        $mp.page.route = $mp.page.is;
    });
    return $mp;
};
/**
 * 统一格式话 路由传递的参数 看看是编码还是非编码 做相应的对策
 *
 * @param {Object} query 当前的路由参数
 * @param {Boolean} getter 是从页面获取 route 对象下的参数 还是编码后传输
 */
export const getFormatQuery = function (query = {}, getter = false) {
    if (Global.Router.CONFIG.encodeURI) {
        if (getter) {
            try {		// 除去微信小程序都不需要 decodeURIComponent
                query = JSON.parse(decodeURIComponent(query.query) || '{}');
            } catch (e) {	// 其他小程序
                query = JSON.parse(query.query || '{}');
            }
        } else {
            try {
                query = JSON.parse(decodeURIComponent(query.query || encodeURIComponent('{}')));
            } catch (e) {
                query = JSON.parse(query.query);
            }
        }
    }
    return query;
};
/**
 * @param {Number} index //需要获取的页面下标 -2:表示获取最后一个即当前页面 -1:表示全部 -3:当前页面的前一个页面
 * @param {Boolean} all //是否获取全部的页面
 */
export const getPages = function (index = -1, all) {
    const pages = getCurrentPages(all);
    if (index === -1) {
        return pages;
    }
    if (index === -2) {
        return pages[pages.length - 1];
    }
    if (index === -3) {
        return pages[pages.length - 2];
    }
    return pages[index];
};
/**
 * 通过一个未知的路径或者名称 在路由表中查找指定路由表 并返回
 * @param {string} type   //path 或者 name
 * @param {Object} routes //当前对象的所有路由表
 */
export const pathOrNameToRoute = function (type, routes = Global.Router.CONFIG.routes) {
    const routesKeys = Object.keys(routes);
    for (let i = 0; i < routesKeys.length; i += 1) {
        const key = routesKeys[i];
        const item = routes[key];
        if (item.path === `/${type}`) {
            return mergeRoute(item); // 合并一下对象,主要是合并 query:{} 及 params:{}
        }
        if (item.path === type) {
            return mergeRoute(item); // 合并一下对象,主要是合并 query:{} 及 params:{}
        }
        if (item.name == type) {
            return mergeRoute(item); // 合并一下对象,主要是合并 query:{} 及 params:{}
        }
    }
    err(`当前 '${type}' 在路由表中没有找到匹配的 name 或者 path`);
};

/**
 * 获取 to 的配置参数
 * @param {Object} rule 当前跳转的规则
 */
export const formatTo = function (finalRoute) {
    const route = copyObject(finalRoute.route);
    const { rule } = finalRoute;
    route.query = rule.query || rule.params || {};
    return route;
};

/**
 * 获取 from 的配置参数 from 页面永远都是站在当前页面忘其它地方走 所以都是最后一个页面
 *
 * @param {Object} routes //当前对象的所有路由表
 */
export const formatFrom = function (routes) {
    const topPage = getPages(-2);
    const { page, query } = getPageVmOrMp(topPage, false);
    const route = pathOrNameToRoute(page.route, routes);	// 获取到当前路由表下的 route
    route.query = getFormatQuery(query);	// 不管是编码传输还是非编码 最后都得在 to/from 中换成json对象
    return route;
};

/**
 *
 * 把用户的跳转路由规则格式化成uni-app可用的路由跳转规则
 *
 * @param {Object} rule  //当前用户跳转的路由规则
 * @param {Object} routes //当前simple-router 下的路由表
 */
export const ruleToUniNavInfo = function (rule, routes) {
    if (rule == null) {
        return err('当前跳转规则为空,请检查跳转代码');
    }
    // eslint-disable-next-line
    let [navType, route, query] = ['path', null, {}];
    if (rule.constructor === String) {		// 是字符串类型 那当前就是路径啦
        route = pathOrNameToRoute(rule, routes);	// 直接把 rule 当 path 传递 完事
    } else if (rule.constructor === Object) {		// 对象类型 可以是 path 或者 name
        route = pathOrNameToRoute(rule.path || (navType = 'name', rule.name), routes);	// 两则必有其一 报错自己处理
        query = rule.query || rule.params || {};
    } else {
        return err('传的什么乱七八糟的类型?路由跳转规则只认字符串 \'path\' , 对象 \'path\' , 对象 \'name\' ');
    }
    // 路径处理完后   开始格式化参数
    const uniRoute = parseQuery(route.path, query);	// uni-app 需要的跳转规则
    return {
        rule,
        route,
        uniRoute,
    };
};
/**
 * 获取当前页面下的 Route 信息
 *
 * @param {Object} pages 获取页面对象集合
 * @param {Object} Vim 用户传递的当前页面对象
 */
export const AppletsPageRoute = function (pages, Vim) {
    let [query, path] = [{}, ''];
    const page = pages[pages.length - 1];	// 获取到当前页面
    if (pages.length > 0) {
        const uniQuery = getPageVmOrMp(page, false).query;
        query = getFormatQuery(uniQuery, true);
        path = page.route;
    } else if (Vim != null) {
        query = getFormatQuery(Vim.$mp.page.options, true);
        path = page.route;
    }
    const route = pathOrNameToRoute(path);
    route.query = query;
    return route;
};
