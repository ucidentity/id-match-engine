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
              "/v1/people/$SOR?/$sorId?"(controller : "person") {
                action = [GET : "getSorUser", PUT : "createOrUpdate", DELETE : "deleteSorUser" ] }
              "/v1/people/$SOR?" (controller : "person") {
                action = [GET: "getSorUsers"]  }

              /*Matching API */
             "/v1/engine"(controller : "engine") {
                action = [GET : "findMatches"] }
             "/v1/engine/canonical"(controller : "engine") {
                action = [GET : "findCanonicalMatches"] }
             "/v1/engine/fuzzy"(controller : "engine") {
                action = [GET : "findFuzzyMatches"] }
         }
}

