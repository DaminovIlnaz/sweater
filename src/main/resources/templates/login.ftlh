<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

    <@c.page>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div CLASS="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if message??>
    <div CLASS="alert alert-${messageType}" role="alert">
        ${message}
    </div>
    </#if>
    <@l.login "/login" false/>
    </@c.page>