package com.fujias.qwmini.entity;


/**
 *  
 * @description: 企业微信成员类
 * @author yuyongqiang
 * @date 2021/7/29 17:42
 */
public class Member {

    /*成员UserID*/
    private String userid;
    /*成员名称*/
    private String name;
    /*成员手机*/
    private String mobile;
    /*成员所属部门*/
    private String department;
    /*部门内的排序值*/
    private String order;
    /*职务信息*/
    private String position;
    /*性别 0未定义 1 男  2 女 */
    private String gender;
    /*邮箱*/
    private String email;
    /*在部门内是否为上级*/
    private String is_leader_in_dept;
    /*头像url*/
    private String avatar;
    /*头像缩略图*/
    private String thumb_avatar;
    /*座机*/
    private String telephone;
    /*别名*/
    private String alias;
    /*拓展属性*/
    private String extattr;
    /*激活状态* 1=已激活，2=已禁用，4=未激活，5=退出企业。*/
    private String status;
    /*员工二维码url*/
    private String qr_code;
    /*员工对外属性*/
    private String external_profile;
    /*对外展示视频号名称*/
    private String nickname;
    /*对外展示视频号状态*/
    private String status2;
    /*地址*/
    private String address;
    /*全局唯一*/
    private String open_userid;
    /*主部门*/
    private String main_department;

    /**
     *
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     *
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIs_leader_in_dept() {
        return is_leader_in_dept;
    }

    public void setIs_leader_in_dept(String is_leader_in_dept) {
        this.is_leader_in_dept = is_leader_in_dept;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getThumb_avatar() {
        return thumb_avatar;
    }

    public void setThumb_avatar(String thumb_avatar) {
        this.thumb_avatar = thumb_avatar;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getExtattr() {
        return extattr;
    }

    public void setExtattr(String extattr) {
        this.extattr = extattr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getExternal_profile() {
        return external_profile;
    }

    public void setExternal_profile(String external_profile) {
        this.external_profile = external_profile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpen_userid() {
        return open_userid;
    }

    public void setOpen_userid(String open_userid) {
        this.open_userid = open_userid;
    }

    public String getMain_department() {
        return main_department;
    }

    public void setMain_department(String main_department) {
        this.main_department = main_department;
    }
}
