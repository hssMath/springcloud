package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装实体类返回信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    public  String message;
    private T data;

    public CommonResult(Integer code, String message){
        this(code,message,null);
    }
}
