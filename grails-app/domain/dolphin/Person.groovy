/*
SOFTWARE COPYRIGHT NOTICE AND DISCLAIMER
All software developed at UC Berkeley should be copyright protected and thereby bear a clear, standardized copyright notice along with a disclaimer notice. This copyright and disclaimer notice protects the software author(s) and the University of California from any liability that might result, however remote, from the use of the software. Accordingly, the use of this notice is especially applicable to software made available for use beyond the author(s), and includes software distributed as "freeware" or open source via either computer networks, the Office of Technology Licensing, or other UC Berkeley organizations.

For software distributed with source code, the copyright and disclaimer notice should be imbedded within each file. For software distributed on physical media (e.g. CD ROM), the copyright and disclaimer notice should be printed on an external label (as well as in any source code). For software binary code distributed over a network, the copyright notice should be embedded in a "readme.txt" file that is sent along with the software. If practicable, the copyright notice should appear on an opening binary code display.

The approved format for the UC Berkeley Copyright and Disclaimer Notice is appended below. An acknowledgement of the software author(s) can be added to this notice. Note that the following copyright and disclaimer language assumes the most likely application of the UC Copyright Policy -- which is that the University is the sole owner of the software. Your particular software ownership situation might differ; for example, you might solely own the software, or there might be joint ownership of the software. If you have questions regarding software ownership, or any other questions about copyrightable software, then contact the Office of Technology Licensing or go to copyrightable software FAQs. 

UC Berkeley's Standard Copyright and Disclaimer Notice:

Copyright ©20xx [see Other Notes, below]. The Regents of the University of California (Regents). All Rights Reserved. Permission to use, copy, modify, and distribute this software and its documentation for educational, research, and not-for-profit purposes, without fee and without a signed licensing agreement, is hereby granted, provided that the above copyright notice, this paragraph and the following two paragraphs appear in all copies, modifications, and distributions. Contact The Office of Technology Licensing, UC Berkeley, 2150 Shattuck Avenue, Suite 510, Berkeley, CA 94720-1620, (510) 643-7201, for commercial licensing opportunities.

[Optional: Created by John Smith and Mary Doe, Department of Statistics, University of California, Berkeley.]

IN NO EVENT SHALL REGENTS BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF REGENTS HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

REGENTS SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". REGENTS HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

 

[The second and third paragraphs must be in all capital letters to comply with the California Commercial Code as well as with other States' versions of Uniform Commercial Code Article 2.]

Other Notes: Insert year software was first published as well as any subsequent years when a modified version is published. Publication is defined in the U.S. Copyright Act as the distribution or offer of distribution of a work to the public by sale or other transfer of ownership or by rental, lease, or lending. Since some keyboards or fonts cannot reproduce the © symbol, the symbol (c) may be used instead, although the latter will not always be accepted as a substitute for the former. Use of the symbol "©" in combination with certain other requirements affords copyright protection in some foreign countries.
*/

package dolphin

/*
 * alternative schema for User domain class
 * allows any number of attributes, without any dependency on table columns like in User class
 */

import dolphin.Attribute;

class Person {

    //uid column replaces the default grails id column
    static mapping = { 
            id name: 'uid'
            version false
    }
    String uid;
    static hasMany = [ attributes : Attribute ]
     
}
