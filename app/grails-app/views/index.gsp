<html>
<head>
    <asset:stylesheet src="home.css"/>
    <title><g:message code="home.title" default="Grails Slack Signup"/></title>
    <style type="text/css">
        body {
            background-image: url(${assetPath(src: 'home.jpg')});
        }
    </style>
</head>
<body>
<div id="content" role="main">

    <asset:image src="slack.svg" width="200" alt="Slack"/>
    <h1>Grails Community Slack</h1>
    <p>Please provide information about yourself in the second field, including real name, current job title, and employer (or better a link to your LinkedIn profile). Without this information, your invite will not be approved. We request this information for approval purposes and is due to previous abuse of the system and to avoid disruptive members and spam.</p>
    <g:form controller="invite" action="request">
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
        <input type="submit" value="${g.message(code: 'invite.requestInvitation', default: 'Request Invitation')}"/>

        <p>We'd love you to join our team on Slack. Submit the form above to receive an invitation email from Slack. If you're not on Slack, you'll need to create a free account as part of the process.</p>
    </g:form>

</div>
</body>
</html>