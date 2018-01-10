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
                <td>${communityUser.id}</td>
                <td>${communityUser.about}</td>
                <td>${communityUser.status}</td>
                <td>
                    <g:form controller="communityUser" action="approve">
                        <g:hiddenField name="email" value="${communityUser.id}"/>
                        <input type="submit" value="${g.message(code: 'communityUser.approve', default: 'Approve')}"/>
                    </g:form>
                    <g:form controller="communityUser" action="reject">
                        <g:hiddenField name="email" value="${communityUser.id}"/>
                        <input type="submit" value="${g.message(code: 'communityUser.reject', default: 'Reject')}"/>
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
                    controller="communityUser"
                    action="index"/>
    </div>

    <hr/>

    <g:form controller="communityUser" action="requestInvite">
        <ol>
            <li>
            <label><g:message code="communityUser.email" default="Email"/></label>
            <g:textField name="email"/>
            </li>
            <li>
                <label><g:message code="communityUser.about" default="About"/></label>
                <g:textArea name="about"/>
            </li>
        </ol>
        <input type="submit" value="${g.message(code: 'communityUser.requestInvitation', default: 'Request Invitation')}"/>
    </g:form>





</div>
</body>
</html>