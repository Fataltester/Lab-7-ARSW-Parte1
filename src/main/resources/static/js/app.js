var app = (function () {
    var author = null;
    var blueprints = [];

    return {
        // Cambiar autor actual
        setAuthor: function (authorName) {
            author = authorName;
        },

        // Cargar planos de un autor
        getBlueprints: function (authorName) {
            author = authorName;

            apimock.getBlueprintsByAuthor(author, function (bps) {
                if (!bps) {
                    alert("No se encontraron planos para " + author);
                    $("#blueprintstable").find("tr:gt(0)").remove();
                    $("#totalpoints").text("Total user points: 0");
                    $("#showfname").text("nombre del autor");
                    return;
                }

                // Transformar los planos -> objetos {name, points}
                blueprints = bps.map(function (bp) {
                    return { name: bp.name, points: bp.points.length };
                });

                // Limpiar tabla y encabezado
                $("#blueprintstable").find("tr:gt(0)").remove();
                $("#showfname").text(author + "'s blueprints:");

                // Agregar filas a la tabla
                blueprints.map(function (bp) {
                    var row = `<tr>
                                 <td>${bp.name}</td>
                                 <td>${bp.points}</td>
                               </tr>`;
                    $("#blueprintstable").append(row);
                });

                // Calcular total con reduce
                var totalPoints = blueprints.reduce(function (acc, bp) {
                    return acc + bp.points;
                }, 0);

                // Actualizar DOM
                $("#totalpoints").text("Total user points: " + totalPoints);
            });
        }
    };
})();

// Asociar operación al botón del formulario

$(document).ready(function () {
    $("#getBlueprintsBtn").on("click", function () {
        var author = $("#fname").val();
        app.getBlueprints(author);
    });
});