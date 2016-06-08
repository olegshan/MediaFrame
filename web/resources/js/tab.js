$(document).ready(function () {

    $("#fileUpload").on('change', function () {

        //get count of selected files
        var countFiles = $(this)[0].files.length;
        var imgPath = $(this)[0].value;
        var extn = imgPath.substring(imgPath.lastIndexOf('.') + 1).toLowerCase();
        var image_holder = $("#image-holder");
        image_holder.empty();

        if (extn == "gif" || extn == "png" || extn == "jpg" || extn == "jpeg") {
            if (typeof(FileReader) != "undefined") {

                //create the table
                var newElem = document.createElement('table');
                newElem.id = 'tl';
                newElem.align = 'center';
                newElem.border = 0;


                //load all files using Promise
                function loadImage(image) {
                    return new Promise(function (resolve, reject) {
                        var fileReader = new FileReader();
                        fileReader.onload = function (e) {
                            resolve(e.target.result);
                        };
                        fileReader.readAsDataURL(image);
                    });
                }

                //put loaded files into the queue
                var queue = Promise.resolve();

                [].reduceRight.call(this.files, function (queue, file, index) {
                    return queue.then(function () {
                        return loadImage(file).then(function (imageAsDataUrl) {

                            //attach rows with cells to the table
                            var newRow = newElem.insertRow(0);
                            var newCell1 = newRow.insertCell(0);
                            newCell1.innerHTML = "<input type='text' class='form-control' " +
                                "placeholder='Source' name='source' style='margin: 15px'>";
                            var newCell2 = newRow.insertCell(0);
                            newCell2.innerHTML = "<input type='text' class='form-control' " +
                                "placeholder='Tags' name='tags' style='margin: 10px'>";
                            var newCell3 = newRow.insertCell(0);
                            newCell3.innerHTML = "<input type='text' class='form-control' " +
                                "placeholder='Name' name='name' style='margin-left: 5px'>";
                            var newCell4 = newRow.insertCell(0);

                            $("<img />", {
                                "src": imageAsDataUrl,
                                "class": "thumb-image"
                            }).appendTo(newCell4);
                        });
                    });
                }, Promise.resolve()).then(function () {

                    //everything is ready, we can attach the table to imageholder and show it
                    document.getElementById("image-holder").appendChild(newElem);
                    image_holder.show();
                });

            } else {
                alert("This browser does not support FileReader.");
            }
        } else {
            alert("Only .jpg, .jpeg, .png and .gif formats are supported.");
        }
    });
});

