<html>
<head>
    <title><g:message code="img.invite.request" default="Request Invite"/></title>
    <meta name="layout" content="main" />
</head>
<body>
<div id="content" role="main">
    <table>
        <thead>
            <tr>
                <th><g:message code="communityUser.email" default="Email"/></th>
                <th><g:message code="communityUser.about" default="About"/></th>
                <th><g:message code="communityUser.status" default="Status"/></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
        <g:each var="communityUser" in="${communityUserList}">
            <tr>
                <td>${communityUser.email}</td>
                <td>${communityUser.about}</td>
                <td>${communityUser.status}</td>
                <td>
                    <g:form controller="invite" action="approve">
                        <g:hiddenField name="email" value="${communityUser.email}"/>
                        <input type="submit" value="${g.message(code: 'invite.approve', default: 'Approve')}"/>
                    </g:form>
                    <g:form controller="invite" action="reject">
                        <g:hiddenField name="email" value="${communityUser.email}"/>
                        <input type="submit" value="${g.message(code: 'invite.reject', default: 'Reject')}"/>
                    </g:form>
                </td>
            </tr>
        </g:each>

        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${pagination.total}"
                    offset="${pagination.offset}"
                    max="${pagination.max}"
                    controller="invite"
                    action="index"/>
    </div>

</div>
</body>
</html>