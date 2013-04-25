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
               "/pendingTask"(controller : "pendingReconcilerTask", action : "list")
               "/pendingTask/$id"(controller : "pendingReconcilerTask", action : "show" )
               
	}
}
