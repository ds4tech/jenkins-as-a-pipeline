import groovy.json.JsonSlurper

String metadata = """
                    [
                      {
                        "name": "jobdsl-config-server",
                        "full_name": "dev-trainings/jobdsl-config-server",
                        "owner": {
                          "login": "dev-trainings"
                        },
                        "private": false
                      },
                      {
                        "name": "jobdsl-service-discovery",
                        "full_name": "dev-trainings/jobdsl-service-discovery",
                        "owner": {
                          "login": "dev-trainings"
                        },
                        "private": false
                      }
                    ]
                    """

def json = new JsonSlurper().parseText(metadata)

print "${json[0].name} project owner: ${json[0].owner.login}"