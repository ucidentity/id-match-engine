<html>
<head>
<title> Search</title>
</head>
<body>

<br><b>Enter the person profile to search or match </b>

<g:form action="match" >
<br><label for="firstname">Firstname :</label><g:textField name="firstName" value="" />
<br><label for="lastname">Lastname :</label><g:textField name="lastName" value="" />
<br><label for="daetOfBirth">Date Of Birth :</label><g:textField name="dateOfBirth" value="" />
<br><label for="SocialSecNumber">Social Security Number :</label><g:textField name="social" value="" />
<br><label for="autoCreate">Auto Create if no exact or recon matches :</label><g:checkBox name="autoCreate" value="${true}" />
<br><label for="exactMatchScore">For Exact Match, Score Should exceed :</label><g:textField name="exactMatchScore" value="90"/>
<br><label for="reconMatchScore">For Manual Recon Match, Score Should exceed :</label><g:textField name="reconMatchScore" value="80"/>
<br><g:submitButton name="Run Match" value="Run Match"/>
</g:form>


<br><b>The available persons for testing are : </b></br>
<ol>
    <g:each var="person" in="${persons}">
     <li> ${person.firstName} ${person.lastName} ${person.social} ${person.dateOfBirth}</li>
    </g:each>

</ol>
</body>
</html>
