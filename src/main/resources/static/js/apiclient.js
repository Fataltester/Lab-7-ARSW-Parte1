
var apiclient = {
    getBlueprintsByAuthor: function (authorName, callback) {
        $.ajax({
            url: `http://localhost:8080/blueprints/${authorName}`, 
            method: 'GET',
            success: function (data) {
                console.log(data)
                if (data && data.length > 0) {
                    callback(data);  // callback cuando se reciben datos
                } else {
                    callback("No se encontraron planos para el autor " + authorName, null);  // no hay datos
                }
            },
            error: function (xhr, status, error) {
                callback("Error al obtener los planos: " + error, null);  // error de peticion
            }
        });
    },
    // Obtener un plano específico por autor y nombre de plano
    getBlueprintsByNameAndAuthor: function (authorName, blueprintName, callback) {
        $.ajax({
            url: `http://localhost:8080/${authorName}/${blueprintName}`,
            method: 'GET',
            success: function (data) {
                if (data) {
                    callback(data);  // callback cuando hay datos
                } else {
                    callback("No se encontró el plano " + blueprintName + " del autor " + authorName, null);  // no hay datos
                }
            },
            error: function (xhr, status, error) {
                callback("Error al obtener el plano: " + error, null);  // Error de peticion
            }
        });
    }
};



