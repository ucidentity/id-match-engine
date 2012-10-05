<%--
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
--%>
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
