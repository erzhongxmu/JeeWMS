/**
 * 公共方法工具类
 */

/**
 * 函数节流方法
 * 在高频触发回调函数时，节流操作使回调函数在每隔一段时间定期执行一次，时间间隔内再触发，不会重新执行。
 * 核心在于让一个函数不要执行的太频繁，减少一些过快的操作。
 * @param {*} fn 是我们需要包装的事件回调
 * @param {*} wait 是每次推迟执行的等待时间
 */
export function throttle(fn, wait = 1500){
  let inThrottle, lastFn, lastTime;
  return function() {
    const context = this,
      args = arguments;
    if (!inThrottle) {
      fn.apply(context, args);
      lastTime = Date.now();
      inThrottle = true;
    } else {
      clearTimeout(lastFn);
      lastFn = setTimeout(function() {
        if (Date.now() - lastTime >= wait) {
          fn.apply(context, args);
          lastTime = Date.now();
        }
      }, Math.max(wait - (Date.now() - lastTime), 0));
    }
  };
};

/**
 * 函数防抖方法
 * 在高频触发回调函数时，防抖操作使回调函数在一定时间间隔内，再次触发会清空定时器，并重新计时；计时结束后输出一次结果。
 * 核心在于，在短时间内大量触发同一事件时，只会执行一次回调函数。避免把一次事件误认为多次。
 * @param {*} fn  是我们需要包装的事件回调
 * @param {*} delay 是每次推迟执行的等待时间
 */
export function debounce(fn, delay = 1000){
  // 定时器
  let timer = null;
  // 将debounce处理结果当作函数返回
  return function() {
    // 保留调用时的this上下文
    let context = this;
    // 保留调用时传入的参数
    let args = arguments;
    // 每次事件被触发时，都去清除之前的旧定时器
    if (timer) {
      clearTimeout(timer);
    }
    // 设立新定时器
    timer = setTimeout(function() {
      fn.apply(context, args);
    }, delay);
  };
};

/**
 * 数字格式化–三个数字一个逗号
 * num:需要格式化的数字，可以含有3位小数点
 * */
export function numberDivide(num) {
	return parseFloat(num).toLocaleString();
}

/*
 ** randomWord() 产生任意长度随机字母数字组合
 ** randomFlag 是否任意长度 min 任意长度最小位[固定位数] max 任意长度最大位
 ** 生成6—12位随机字符串 ：randomWord(true,6,12)
 ** 生成随机的6位字符串 ： randomWord(false,6)
 */
export function randomWord(randomFlag = true, min = 6, max) {
	let str = "",
		range = min,
		arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '-'
		];
	//'-','.','~','!','@','#','$','%','^','&','*','(',')','_',':','<','>','?'
	if (randomFlag) {
		range = Math.round(Math.random() * (max - min)) + min; // 任意长度
	}
	for (let i = 0; i < range; i++) {
		var pos = Math.round(Math.random() * (arr.length - 1));
		str += arr[pos];
	}
	return str;
}

/**
 * 获取随机数
 * min:取值下限，max:取值上限
 * */
export function numberRandom(min=1, max=100) {
	return Math.floor(Math.random() * (max - min + 1) + min)
}

/**
 * 判断系统是不是IOS
 * */
export function isIOS() {
	let u = navigator.userAgent
	let isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/) //IOS终端
	return isIOS
}

/**
 * 日期时间格式化函数
 * date：传入的日期时间，fmt：需要转换的格式
 * */
export function dateFormat(date, fmt = 'YYYY-MM-DD HH:mm:ss') {
	if (!date) {
		return ''
	}
	if (typeof date === 'string') {
		date = new Date(date.replace(/-/g, '/'))
	}
	if (typeof date === 'number') {
		date = new Date(date)
	}
	var o = {
		'M+': date.getMonth() + 1,
		'D+': date.getDate(),
		'h+': date.getHours() % 12 === 0 ? 12 : date.getHours() % 12,
		'H+': date.getHours(),
		'm+': date.getMinutes(),
		's+': date.getSeconds(),
		'q+': Math.floor((date.getMonth() + 3) / 3),
		'S': date.getMilliseconds()
	}
	var week = {
		'0': '\u65e5',
		'1': '\u4e00',
		'2': '\u4e8c',
		'3': '\u4e09',
		'4': '\u56db',
		'5': '\u4e94',
		'6': '\u516d'
	}
	if (/(Y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? '\u661f\u671f' : '\u5468') :
			'') + week[date.getDay() + ''])
	}
	for (var k in o) {
		if (new RegExp('(' + k + ')').test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
		}
	}
	return fmt
}

/**
 * 获取星期几
 * @param date
 * @param fmt
 * @returns {*}
 */
export function getWeekDate(date) {
	if (!date) {
		return ''
	}
	let now = new Date(date);
	let day = now.getDay();
	let weeks = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	let week = weeks[day];
	return week;
}
