package pojo;

import java.io.Serializable;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-16 8:22
 */
public class User implements Serializable,Comparable<User> {
    private String name;
    private String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return this.name.equals(user.name) && (this.password.equals(user.password));
        }
        return false;
    }

    @Override
    public int compareTo(User user) {
//        //先比较用户名
//        int compareResult = this.name.compareTo(user.getName());
//        //用户名相同再比较密码,如果名字不同则直接返回比较名字的结果
//        return compareResult == 0 ? this.password.compareTo(user.getPassword()) : compareResult;

        //先比较密码
        int compareResult = this.password.compareTo(user.getPassword());
        //密码相同再比较用户名,如果密码不同则直接返回比较密码的结果
        return compareResult == 0 ? this.name.compareTo(user.getName()) : compareResult;
    }

    @Override
    public String toString() {
        return "pojo.User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
