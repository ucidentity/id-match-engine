class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')

               /* Pending Reconciler Tasks */
               "/pendingMatches"(controller : "pendingReconcilerTask", action : "getPendingMatches")
               "/pendingMatches/$id?"(controller : "pendingReconcilerTask", action : "getPendingMatch" )

              /* People API */
              "/people/$sorId?/$id?" (controller : "people", action : "getUserBySor" )
              "/people/$sorId?" (controller : "people", action : "getAllBySor" ); 
              "/people/$id?" (controller : "people" , action : "getUser" );
              "/people" (controller : "people" , action : "getUsers" );

            
	}
}
