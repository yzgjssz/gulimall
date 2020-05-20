package com.atguigu.gulimall.product.exception;

import com.atguigu.common.exception.BizCodeEnume;
import com.atguigu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @Author：印志刚
 * @Date：2020/5/19 16:09
 * @Description：异常统一处理
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.atguigu.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {

        @ExceptionHandler(value = MethodArgumentNotValidException.class)
        public R handleException(MethodArgumentNotValidException e){
            log.error("数据校验出现问题{},异常类型{}",e.getMessage(),e.getClass());
            BindingResult bindingResult = e.getBindingResult();
            HashMap<String, String> map = new HashMap<>();
            bindingResult.getFieldErrors().forEach((item) ->{
                String defaultMessage = item.getDefaultMessage();
                String field = item.getField();
                map.put(field,defaultMessage);
            });
            return R.error(400,"字段错误").put("data",map);
        }
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable){

        log.error("错误：",throwable);
        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(),BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }
}
