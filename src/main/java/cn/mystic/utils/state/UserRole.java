package cn.mystic.utils.state;

/**
 * Created by lihao on 2018/1/26.
 */
public enum UserRole {

    /**
     * {@code 0 超级管理员}.
     */
    ADMIN(0),

    /**
     * {@code 1 经理}.
     */
    MANAGER(1),

    /**
     * {@code 2 员工}.
     */
    EMPLOYEE(2);

    private final Integer value;
    public Integer value() {
        return this.value;
    }

    UserRole(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
