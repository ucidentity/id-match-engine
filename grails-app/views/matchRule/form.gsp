<html>
<head>
<title> Create a Matching Rule</title>
</head>
<body>

<b>Configure the matching rules here</b>
<p>For each rule select the attribute to match against, the type of algorithm to use, and the score to assign in case of the match</s>
<g:form action="add" >

 <br>Match <g:select name="attribute" from="${targetAttrList}"/>
 <br>Using algorithm <g:select name="algorithm" from="${algoTypeList}"/> 
 <br>Set score to: <g:textField name="score" value="" />
 <br><g:submitButton name="Add" value="Add"/>
</g:form>
<b>The list of rules added are : </b>
<br>Attribute | Algorithm Type | Score | Distance(in case of Edit Algorithms)
<ol>
    <g:each var="rule" in="${rules}">
     <li>${rule.attribute} | ${rule.algorithm} | ${rule.score} | ${rule.distanceLength} </li>
    </g:each>

</ol>

</body>
</html>
