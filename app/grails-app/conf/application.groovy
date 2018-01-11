environments {
    development {
        grails {
            serverURL = System.getenv('NGROK_URL') ?: 'http://localhost:8080'
        }
    }
}
