<html>
<head>
<title>Match Results </title>
</head>
<body>
<br><b>Search Params are:</b>
<br>${params.firstName} ${params.lastName} ${params.social} ${params.dateOfBirth}
<br>
<br><b>Exact Match Scores are: </b>
<ul>
    <g:each var="person" in="${exactMatchMap}">
     <li> ${person}</li>
    </g:each>

</ul>

<br><b>Recon Match Scores are: </b>
<ul>
    <g:each var="person" in="${reconMatchMap}">
     <li> ${person}</li>
    </g:each>

</ul>

<g:form action="search">
<br><g:submitButton name="Back To Search" value="Back To Search"/>
</g:form>
<br><b>The available persons for testing are : </b>

<ul>
    <g:each var="person" in="${persons}">
     <li> ${person.firstName} ${person.lastName} ${person.social} ${person.dateOfBirth}</li>
    </g:each>

</ul>


</body>
</html>
