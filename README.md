#FRS Create Case 

This project is an integration piece to fit in with FRS JSON they are sending to create a case.

The main FrsController accepts their json (see example in src/main/resources called FrsCreateCase.json

This is what it does (these comments are taken from the controller)

This REST service takes Frs CreateCase.json and pushes several REST calls to Polonious.
 
1) Standard Create Case with Mapping. Steve is mapping the fields we pass over for the create using Polonious Create Case API
2) 1 or many Find Person with originalSystemId calls. 
3) 1 or many Create Person /v3 which allows attributes to also be updated.
4) 1 or many Find Asset with originalSystemId calls. 
5) 1 or many Create Asset /v3 which allows attributes to also be updated.
6) 1 or many Create Task Person Role links.
7) 1 or many Create Task Asset Role links.

To test this, first get an OAuth token then run this curl command with your token instead of mine.

curl -v POST http://localhost:8080/frs/createCase -d @src/main/resources/FrsCreateCase.json

If the application.properties are setup correctly, this will process the json, authenticate to polonious and post the case with it's details.

To test individual Polonious APIs you might need.	

## POST PersonRole

curl  -vX POST --header "Authorization: Bearer c88027a6-d50d-44ab-bb42-8881c1d5fcdb" --header "Content-Type: application/json" -d@taskpersonrole.json http://localhost:8080/demoau/public/oauth/task/v1/taskPersonRole

## POST AssetRole

curl  -vX POST --header "Authorization: Bearer 5a8ca5ed-7fed-4883-8fcb-736a9f2c0cc0" --header "Content-Type: application/json" -d@taskassetrole.json http://localhost:8080/demoau/public/oauth/task/v1/taskAssetRole

## POST Asset v3

Now with attributes posting. This does a create then finds any attributes added to the JSON and updates the work unit attributes with the values.

curl  -vX POST --header "Authorization: Bearer 2bbc5f5d-4b68-40bc-a741-086407d3b1fa" --header "Content-Type: application/json" -d@asset.json http://localhost:8080/demoau/public/oauth/asset/v3

## POST Person v3

Now with attributes posting. This does a create then finds any attributes added to the JSON and updates the work unit attributes with the values.

curl  -vX POST --header "Authorization: Bearer 0f65d56f-51c7-40a0-bb45-589f52c80784" --header "Content-Type: application/json" -d@person.json http://localhost:8080/demoau/public/oauth/person/v3

## GET Person

This was enhanced to allow searches on originalSystemId

curl  -v GET --header "Authorization: Bearerc477622c-2411-479e-88cd-7f8a73ff5ca0" --header "Content-Type: application/json"  http://localhost:8080/demoau/public/oauth/person/v2?originalSystemId=cc:4016


## Get Asset

This was enhanced to allow searches on originalSystemId


