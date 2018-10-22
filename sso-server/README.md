1 POST /v2/ticket
Content-Type:application/x-www-form-urlencoded
参数：
username:admin
password:123
service:http://localhost:8083
成功返回：
http status：200
内容：这个就是登陆成功后返回的票据
{
    "ticket": "ST-1-D0Xyie64Tbtj82MdWiyAne5Jp1MSKY-20171031ICW"
}

失败返回：
1)
http status：401
失败原因：密码错误
内容：
{
  "@class" : "java.util.HashMap",
  "authentication_exceptions" : [ "java.util.ArrayList", [ "AccountNotFoundException: admin not found with SQL query", "FailedLoginException: Password does not match value on record." ] ]
}
2)
http status：401
失败原因：用户不存在
内容：
{
  "@class" : "java.util.HashMap",
  "authentication_exceptions" : [ "java.util.ArrayList", [ "AccountNotFoundException: admin2 not found with SQL query", "AccountNotFoundException: admin2 not found with SQL query" ] ]
}
3)
http status：500
失败原因：未授权service url
内容：
{
    "ticket": "service.not.authorized.sso"
}
4)
http status：417
失败原因：其他错误
内容：
{
    "ticket": "Login fail,please relogin."
}
5) 缺少service参数或参数值
http status：500
{
    "ticket": "Target service/application is unspecified or unrecognized in the request"
}
6)缺少username/password参数
{
    "ticket": "No credentials are provided or extracted to authenticate the REST request"
}


2 GET /v2/ticket
参数：
service:http://localhost:8083
成功返回：
http status：200
内容：这个就是登陆成功后返回的票据
{
    "ticket": "ST-1-D0Xyie64Tbtj82MdWiyAne5Jp1MSKY-20171031ICW"
}
失败返回：
1) TGC过期或不存在
http status：301
内容：
{
    "ticket": "Invalid ticket or not exists."
}
2) 未授权service url
http status：500
内容：
{
    "ticket": "Service unauthorized"
}


CAS Overlay Template
============================

Generic CAS WAR overlay to exercise the latest versions of CAS. This overlay could be freely used as a starting template for local CAS war overlays. The CAS services management overlay is available [here](https://github.com/apereo/cas-services-management-overlay).

# Versions

```xml
<cas.version>5.1.x</cas.version>
```

# Requirements
* JDK 1.8+

# Configuration

The `etc` directory contains the configuration files and directories that need to be copied to `/etc/cas/config`.

# Build

To see what commands are available to the build script, run:

```bash
./build.sh help
```

To package the final web application, run:

```bash
./build.sh package
```

To update `SNAPSHOT` versions run:

```bash
./build.sh package -U
```


## Executable WAR

Run the CAS web application as an executable WAR.

```bash
./build.sh run
```

## Spring Boot

Run the CAS web application as an executable WAR via Spring Boot. This is most useful during development and testing.

```bash
./build.sh bootrun
```

### Warning!

Be careful with this method of deployment. `bootRun` is not designed to work with already executable WAR artifacts such that CAS server web application. YMMV. Today, uses of this mode ONLY work when there is **NO OTHER** dependency added to the build script and the `cas-server-webapp` is the only present module. See [this issue](https://github.com/apereo/cas/issues/2334) and [this issue](https://github.com/spring-projects/spring-boot/issues/8320) for more info.


## Spring Boot App Server Selection
There is an app.server property in the pom.xml that can be used to select a spring boot application server.
It defaults to "-tomcat" but "-jetty" and "-undertow" are supported. 
It can also be set to an empty value (nothing) if you want to deploy CAS to an external application server of your choice and you don't want the spring boot libraries included. 

```xml
<app.server>-tomcat<app.server>
```

## Windows Build
If you are building on windows, try build.cmd instead of build.sh. Arguments are similar but for usage, run:  

```
build.cmd help
```

## External

Deploy resultant `target/cas.war`  to a servlet container of choice.
,\
com.toceansoft.cas.support.auth.config.CustomerAuthWebflowConfiguration,\
com.toceansoft.cas.support.auth.config.CustomAuthenticationEventExecutionPlanConfiguration,\
com.toceansoft.cas.support.captcha.config.CaptchaConfiguration,\
com.toceansoft.cas.support.captcha.config.ValidateWebflowConfiguation,\
com.toceansoft.cas.support.single.config.SingleLogoutTriggerConfiguration,\
com.toceansoft.cas.support.validate.config.SSOValidateConfiguration,\
com.toceansoft.cas.support.validate.config.SSOMailValidateConfiguration

