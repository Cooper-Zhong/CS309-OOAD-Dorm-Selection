package cs309_dorm_backend.redis;

public class Result {
    //统一封装操作结果的返回值，以便在应用程序中统一处理成功和失败的情况，并将结果以标准格式返回给调用方。
    private int code;
    private String msg;
    private Object data;

    public Result() {
    }
    public Result(int code) {
        this.code = code;
    }

    public Result(int code, String msg) {
        this(code);
        this.msg = msg;
    }

    public Result(int code, String msg, Object data) {
        this(code, msg);
        this.data = data;
    }

    public static Result success() {
        return new Result(200, "success");
    }

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }
    public static Result success(int code, String msg, Object data, Object data1, Object data2, Object data3, Object data4, Object data5) {
        return new Result(code, msg , data);
    }
    public static Result error() {
        return new Result(500, "error");
    }

}
