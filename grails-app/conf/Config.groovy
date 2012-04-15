// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }


grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

//cloud foundry stuff
grails.plugin.cloudfoundry.username = "danny.alcantara@gmail.com"
grails.plugin.cloudfoundry.password = "dannydev1"

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.views.javascript.library="jquery"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

grails.converters.xml.default.deep = true
grails.converters.json.default.deep=true
// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
        grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
        grails.serverURL = "http://localhost:8080/BitterBidder"
        grails{
            mail {
                host = "smtp.gmail.com"
                port = 465
                username = "bitterbidderdev@gmail.com"
                password = "msse2012"
                props = ["mail.smtp.auth":"true",
                        "mail.smtp.socketFactory.port":"465",
                        "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                        "mail.smtp.socketFactory.fallback":"false"]
            }
        }

    }
    production {
        grails.logging.jul.usebridge = false
        greenmail.disabled=true
        rails.serverURL = "http://www.changeme.com"
        grails{
            mail {
                host = "smtp.gmail.com"
                port = 465
                username = "bitterbidderdev@gmail.com"
                password = "msse2012"
                props = ["mail.smtp.auth":"true",
                        "mail.smtp.socketFactory.port":"465",
                        "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                        "mail.smtp.socketFactory.fallback":"false"]
            }
        }

        // TODO: grails.serverURL = "http://www.changeme.com"
    }

    test {
        grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
        grails.serverURL = "http://localhost:8080/BitterBidder"

    }

}

// log4j configuration
log4j = {
//    debug 'bitterbidder'
//    // Example of changing the log pattern for the default console
//    // appender:
//    //
//    //appenders {
//    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
//    //}
//
    appenders {
        console name: "stdout", threshold: org.apache.log4j.Level.DEBUG
    }

    root {
        info 'stdout'
    }

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    info    'bitterbidder'
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'bitterbidder.Customer'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'bitterbidder.CustomerRole'
grails.plugins.springsecurity.authority.className = 'bitterbidder.Role'
