<--
/* 
 * Copyright ©2012. The Regents of the University of California (Regents).
 * All Rights Reserved. Permission to use, copy, modify, and distribute this
 * software and its documentation for educational, research, and not-for-profit
 * purposes, without fee and without a signed licensing agreement, is hereby
 * granted, provided that the above copyright notice, this paragraph and the
 * following two paragraphs appear in all copies, modifications, and
 * distributions. Contact The Office of Technology Licensing, UC Berkeley, 2150
 * Shattuck Avenue, Suite 510, Berkeley, CA 94720-1620, (510) 643-7201, for
 * commercial licensing opportunities.
 * 
 * Created by Venu Alla, CalNet, IST, University of California, Berkeley
 * 
 * IN NO EVENT SHALL REGENTS BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
 * SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * REGENTS HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * REGENTS SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED
 * HEREUNDER IS PROVIDED "AS IS". REGENTS HAS NO OBLIGATION TO PROVIDE
 * MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */
-->
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
