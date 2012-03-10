class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        // M-1: The main landing page shows listings sorted by the date they were created (most recent first)
        "/"(controller: "listing/list")
        "500"(view:'/error')
	}
}
