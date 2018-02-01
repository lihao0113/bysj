package cn.mystic.utils.state;

/**
 * Created by lihao on 2018/1/26.
 */
public enum TaskState {

    /**
     * {@code 0 未开始}.
     */
    NOTSTARTED(0),

    /**
     * {@code 1 进行中}.
     */
    STARTING(1),

    /**
     * {@code 2 已完成}.
     */
    FINISH(2);

    private final Integer value;
    public Integer value() {
        return this.value;
    }

    TaskState(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
