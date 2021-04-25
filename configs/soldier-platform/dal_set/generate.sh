#/bin/sh

if [ "z" = "z${DB_ADDR}" ];then
    DB_ADDR="127.0.0.1"
fi

if [ "z" = "z${DB_PORT}" ];then
    DB_PORT="3306"
fi

if [ "z" = "z${USER_NAME}" ];then
    USER_NAME="user"
fi

if [ "z" = "z$USER_PASSWD" ];then
    USER_PASSWD="passwd"
fi

cat <<EOF>>$1
<?xml version="1.0" encoding="UTF-8"?>
<dalset>
  <hosts>
    <host name="platform_db" ip="${DB_ADDR}" port="${DB_PORT}"/>
  </hosts>
  <users>
    <user id="platform_db_user" name="${USER_NAME}" password="${USER_PASSWD}"/>
  </users>
  <dbInstances>
    <db name="platform" drive="Mysql" rolename="platform">
      <sets>
        <set index="0">
          <master host="platform_db" weight="1"/>
        </set>
      </sets>
      <tables>
        <table prefix="tallocid_bysect" sliceNum="0" fillZero="false"/>
        <table prefix="tdal_set_description" sliceNum="0" fillZero="false"/>
        <table prefix="tdal_set_host" sliceNum="0" fillZero="false"/>
        <table prefix="tdal_set_role" sliceNum="0" fillZero="false"/>
        <table prefix="tdal_set_role_service_relation" sliceNum="0" fillZero="false"/>
        <table prefix="tdal_set_role_set_relation" sliceNum="0" fillZero="false"/>
        <table prefix="tdal_set_role_table_relation" sliceNum="0" fillZero="false"/>
        <table prefix="tdal_set_table" sliceNum="0" fillZero="false"/>
        <table prefix="tdal_set_user" sliceNum="0" fillZero="false"/>
        <table prefix="troute_info" sliceNum="0" fillZero="false"/>
        <table prefix="troute_version" sliceNum="0" fillZero="false"/>
        <table prefix="tweb_config_info" sliceNum="0" fillZero="false"/>
        <table prefix="tweb_nginx_config" sliceNum="0" fillZero="false"/>
      </tables>
    </db>
    <db name="platform" drive="Mysql" rolename="role_machine">
      <sets>
        <set index="0">
          <master host="platform_db" weight="1"/>
        </set>
      </sets>
      <tables>
        <table prefix="tmachine" sliceNum="0" fillZero="false"/>
      </tables>
    </db>
  </dbInstances>
  <relations>
    <relation serviceName="18" roleName="platform" userId="platform_db_user" dbType="NoType"/>
    <relation serviceName="20" roleName="platform" userId="platform_db_user" dbType="NoType"/>
    <relation serviceName="22" roleName="role_machine" userId="platform_db_user" dbType="NoType"/>
  </relations>
</dalset>
EOF


