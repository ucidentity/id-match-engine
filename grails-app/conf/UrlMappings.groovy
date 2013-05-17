class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')

              
                
               /* Pending Matches */
               "/v1/pendingMatches"(controller : "pendingMatch" ) {
                  action = [GET : "list", PUT : "createOrUpdate"] }
               "/v1/pendingMatches/$id?"(controller : "pendingMatch") {
                  action = [GET : "show", DELETE: "delete"] }

              
              /* People SOR API */
              "/v1/people/$SOR?/$sorId?"(controller : "people") {
                action = [GET : "getSorUser", PUT : "createOrUpdate", DELETE : "deleteSorUser" ] }
              "/v1/people/$SOR?" (controller : "people") {
                action = [GET: "getSorUsers"]  }

              /*Matching API */
             "/v1/engine"(controller : "engine") {
                action = [GET : "findMatches"] }
              }
            
	}
}
