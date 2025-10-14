// variable para realizar el cambio de cliente (apiclient, apimock)
//var client = apimock;
var client = apiclient;

var currentBlueprint = null;
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

            client.getBlueprintsByAuthor(author, function (bps) {
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
                                 <td><button class="open-btn" data-name="${bp.name}">Open</button></td>
                               </tr>`;
                    $("#blueprintstable").append(row);
                });

                // Calcular total con reduce
                var totalPoints = blueprints.reduce(function (acc, bp) {
                    return acc + bp.points;
                }, 0);

                // Actualizar DOM
                $("#totalpoints").text("Total user points: " + totalPoints);

                // Asignar evento click a botones "OPEN"
                $(".open-btn").on("click", function () {
                    var bpName = $(this).data("name");
                    app.drawBlueprint(author, bpName);
                });
            });
        },

        drawBlueprint: function (authorName, blueprintName) {
            client.getBlueprintsByNameAndAuthor(authorName, blueprintName, function (bp) {
                if (!bp) {
                    alert("No se encontró el plano: " + blueprintName);
                    return;
                }

                currentBlueprint = bp;

                // Obtener canvas y contexto
                var canvas = document.getElementById("blueprintCanvas");
                var ctx = canvas.getContext("2d");

                // Limpiar canvas antes de dibujar
                ctx.clearRect(0, 0, canvas.width, canvas.height);

                // Dibujar puntos conectados
                ctx.beginPath();
                ctx.moveTo(bp.points[0].x, bp.points[0].y);
                for (var i = 1; i < bp.points.length; i++) {
                    ctx.lineTo(bp.points[i].x, bp.points[i].y);
                }
                ctx.stroke();

                canvas.onpointerdown = function (event) {
                    if (!currentBlueprint) return;

                    const x = event.offsetX;
                    const y = event.offsetY;
                    console.log(`Click detectado en: (${x}, ${y})`);

                    currentBlueprint.points.push({ x, y });

                    // Redibujar con el nuevo punto
                    ctx.clearRect(0, 0, canvas.width, canvas.height);
                    ctx.beginPath();
                    const pts = currentBlueprint.points;
                    ctx.moveTo(pts[0].x, pts[0].y);
                    for (let i = 1; i < pts.length; i++) {
                        ctx.lineTo(pts[i].x, pts[i].y);
                    }
                    ctx.stroke();
                };


                // Actualizar nombre del plano
                $("#currentBlueprint").text("Current blueprint: " + bp.name);
            });
        },
        saveOrUpdateBlueprint: function () {
            if (!currentBlueprint) {
                alert("Debe abrir un blueprint antes de guardar.");
                return;
            }

            const updatedBlueprint = {
                author: author,
                name: currentBlueprint.name,
                points: currentBlueprint.points
            };

            // Convertir updateBlueprint a promesa
            new Promise((resolve, reject) => {
                client.updateBlueprint(author, currentBlueprint.name, updatedBlueprint, function (err, response) {
                    if (err) reject(err);
                    else resolve(response);
                });
            })
                .then(() => {
                    // Luego del PUT, obtener nuevamente los planos
                    return new Promise((resolve, reject) => {
                        client.getBlueprintsByAuthor(author, function (bps) {
                            if (!bps) reject("No se encontraron planos");
                            else resolve(bps);
                        });
                    });
                })
                .then((bps) => {
                    // Actualizar la tabla y puntos sin recargar toda la lista
                    const updatedPointsCount = currentBlueprint.points.length;

                    // Buscar y actualizar la fila correspondiente en la tabla
                    $("#blueprintstable tr").each(function () {
                        const row = $(this);
                        const nameCell = row.find("td:first").text();
                        if (nameCell === currentBlueprint.name) {
                            row.find("td:nth-child(2)").text(updatedPointsCount);
                        }
                    });

                    // Recalcular total de puntos
                    const totalPoints = bps.reduce((acc, bp) => acc + bp.points.length, 0);
                    $("#totalpoints").text("Total user points: " + totalPoints);

                    alert("Blueprint actualizado correctamente.");
                })
                .catch((err) => {
                    alert("Error: " + err);
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
    $("#btnSave").on("click", function () {
        app.saveOrUpdateBlueprint();
    });
});

