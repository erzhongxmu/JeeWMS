package org.jeecg.common.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import com.base.common.api.vo.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常处理器
 *
 * @Author scott
 * @Date 2019
 */
@RestControllerAdvice
@Slf4j
public class JeecgBootExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(JeecgBootException.class)
	public Result<?> handleRRException(JeecgBootException e){
		log.error(e.getMessage(), e);
		return Result.error(e.getMessage());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public Result<?> handlerNoFoundException(Exception e) {
		log.error(e.getMessage(), e);
		return Result.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Result<?> handleDuplicateKeyException(DuplicateKeyException e){
		log.error(e.getMessage(), e);
		return Result.error("数据库中已存在该记录");
	}

	@ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
	public Result<?> handleAuthorizationException(AuthorizationException e){
		log.error(e.getMessage(), e);
		return Result.noauth("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public Result<?> handleException(Exception e){
		log.error(e.getMessage(), e);
		return Result.error("操作失败，"+e.getMessage());
	}

	/**
	 * @Author 政辉
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result<?> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		StringBuffer sb = new StringBuffer();
		sb.append("不支持");
		sb.append(e.getMethod());
		sb.append("请求方法，");
		sb.append("支持以下");
		String [] methods = e.getSupportedMethods();
		if(methods!=null){
			for(String str:methods){
				sb.append(str);
				sb.append("、");
			}
		}
		log.error(sb.toString(), e);
		//return Result.error("没有权限，请联系管理员授权");
		return Result.error(405,sb.toString());
	}

	 /**
	  * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
	  */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
    	log.error(e.getMessage(), e);
        return Result.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    	log.error(e.getMessage(), e);
        return Result.error("字段太长,超出数据库字段的长度");
    }

    @ExceptionHandler(PoolException.class)
    public Result<?> handlePoolException(PoolException e) {
    	log.error(e.getMessage(), e);
        return Result.error("Redis 连接异常!");
    }


	/**
	 * 当系统出现MethodArgumentNotValidException这个异常时，会调用下面的方法
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)//MethodArgumentNotValidException如果页面传参是json对象如果为空能触发
	public Result jsonErrorHandler(MethodArgumentNotValidException e){
		return getAjaxResult(e.getBindingResult());
	}


	/**
	 * 当系统出现MethodArgumentNotValidException这个异常时，会调用下面的方法
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = BindException.class)//MethodArgumentNotValidException如果页面传参是json对象如果为空能触发
	public Result jsonErrorHandler(BindException e){
		return getAjaxResult(e.getBindingResult());
	}

	/**
	 * 抽像出来的方法
	 * @param bindingResult
	 * @return
	 */
	private Result getAjaxResult(BindingResult bindingResult) {
		List<Map<String, Object>> list = new ArrayList<>();
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		for (ObjectError allError : allErrors) {
			Map<String, Object> map = new HashMap<>(1024);
			map.put("defaultMessage", allError.getDefaultMessage());
			map.put("objectName", allError.getObjectName());
			//注意，这里面拿到具体的某一个属性
			FieldError fieldError = (FieldError) allError;
			map.put("field", fieldError.getField());
			list.add(map);
		}
		return Result.error("后端数据校验异常", list);
	}

}
