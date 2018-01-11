environments {
    development {
        grails {
            serverURL = System.getenv('NGROK_URL') ?:  System.getProperty('NGROK_URL') ?: 'http://localhost:8080'
        }
    }
}
