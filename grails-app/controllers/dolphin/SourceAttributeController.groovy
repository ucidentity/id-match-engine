package dolphin

class SourceAttributeController {

/* custom controller code begin */
    def scaffold = true;

    def mylist() { 
       def mylist = SourceAttribute.list();
       render "<br>source attr is "
       mylist.each{
            render "<br>" +it.name 
       }
     }
/* custom controller code end */



/*
    this is the default controller code
    def index() { }

*/
}
