#!/usr/bin/env bash

curl http://localhost:9095/v1/organizations
curl http://localhost:9095/v1/projects/wr_org_001
curl http://localhost:9095/v1/projects/wr_org_002
curl http://localhost:9095/v1/environments/wr_org_001/wr_proj_001
curl http://localhost:9095/v1/environments/wr_org_001/wr_proj_002
curl http://localhost:9095/v1/environments/wr_org_002/wr_proj_001
curl http://localhost:9095/v1/environments/wr_org_002/wr_proj_002


curl 'http://localhost:9095/v1/projects/org/proj/spec/revisions/latest/source.json?with_env_uuid'

curl 'http://localhost:8080/v1/data/mormont/shayba~dev/?query=SELECT%20MAX(num_of_streams)%20FROM%20messages%20%20SINCE%201%20HOURS%20ago'
curl 'http://localhost:8080/v1/data/mormont/shayba~dev/?query=SELECT%20MAX(num_of_streams)%20FROM%20messages%20%20SINCE%202%20HOURS%20ago'
curl 'http://localhost:8080/v1/data/mormont/shayba~dev/?query=SELECT%20MAX(num_of_streams)%20FROM%20messages%20%20SINCE%203%20HOURS%20ago'
curl 'http://localhost:8080/v1/data/mormont/shayba~dev/?query=SELECT%20MAX(num_of_streams)%20FROM%20messages%20%20SINCE%204%20HOURS%20ago'
curl 'http://localhost:8080/v1/data/mormont/shayba~dev/?query=SELECT%20MAX(num_of_streams)%20FROM%20messages%20%20SINCE%205%20HOURS%20ago'
curl 'http://localhost:8080/v1/data/mormont/shayba~dev/?query=SELECT%20MAX(num_of_streams)%20FROM%20messages%20%20SINCE%206%20HOURS%20ago'
curl 'http://localhost:8080/v1/data/mormont/shayba~dev/?query=SELECT%20MAX(num_of_streams)%20FROM%20messages%20%20SINCE%20f%20HOURS%20ago'

curl 'http://localhost:8080/v1/data/mormont/shayba~dev/?query=SELECT%20_object_id%20FROM%20streams%20OF%20drone%20WHERE%20latest(message)%20%3D%20%27OFF%27'
curl 'http://localhost:8080/v1/data/mormont/shayba~dev/?query=SELECT%20_object_id%20FROM%20streams%20OF%20drone%20WHERE%20latest(message)%20%3E%20%27A%27'
curl 'http://localhost:8080/v1/data/mormont/shayba~dev/?query=SELECT%20object_id,%20timestamp%20FROM%20objects%20OF%20drone%20WHERE%20active%3Dtrue%20UNTIL%205%20minutes%20ago%20LIMIT%204'

curl 'http://localhost:8080/v1/data/org/proj/?query=SELECT%20_object_id%20FROM%20streams%20OF%20vehicle%20WHERE%20latest(speed)%20%3E%2010'

