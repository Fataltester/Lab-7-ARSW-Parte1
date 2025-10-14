//@author hcadavid

apimock = (function () {

	var mockdata = {};

	mockdata["johnconnor"] = [{ author: "johnconnor", "points": [{ "x": 150, "y": 120 }, { "x": 215, "y": 115 }], "name": "house" },
	{ author: "johnconnor", "points": [{ "x": 340, "y": 240 }, { "x": 15, "y": 215 }], "name": "gear" }];
	mockdata["maryweyland"] = [{ author: "maryweyland", "points": [{ "x": 140, "y": 140 }, { "x": 115, "y": 115 }], "name": "house2" },
	{ author: "maryweyland", "points": [{ "x": 140, "y": 140 }, { "x": 115, "y": 115 }], "name": "gear2" }];

	return {
		getBlueprintsByAuthor: function (authname, callback) {
			callback(
				mockdata[authname]
			);
		},

		getBlueprintsByNameAndAuthor: function (authname, bpname, callback) {

			callback(
				mockdata[authname].find(function (e) { return e.name === bpname })
			);
		},

		updateBlueprint: function (authorName, blueprintName, blueprintObj, callback) {
            console.log(`(Mock) Actualizando blueprint ${blueprintName} del autor ${authorName}`);

            const authorData = mockdata[authorName];
            if (!authorData) {
                callback(`Autor ${authorName} no encontrado`, null);
                return;
            }

            const index = authorData.findIndex(bp => bp.name === blueprintName);
            if (index === -1) {
                callback(`Blueprint ${blueprintName} no encontrado`, null);
                return;
            }

            // ✅ Actualiza los puntos en el mock
            authorData[index].points = [...blueprintObj.points];

            console.log("(Mock) Blueprint actualizado correctamente:", authorData[index]);
            callback(null, authorData[index]);
        }
	};

})();

/*
Example of use:
var fun=function(list){
	console.info(list);
}

apimock.getBlueprintsByAuthor("johnconnor",fun);
apimock.getBlueprintsByNameAndAuthor("johnconnor","house",fun);*/