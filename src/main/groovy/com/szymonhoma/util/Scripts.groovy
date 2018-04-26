package com.szymonhoma.util

class Scripts {

    /**
     *
     * @param path to script that resides in resources/scripts
     * @return script
     */
    static String loadScript(String scriptPath) {
        def resource = Scripts.class.getResource("/scripts/${scriptPath}")
        if (resource == null) {
            throw new IllegalArgumentException("${scriptPath} script does not exists")
        }
        return resource.text
    }
}
