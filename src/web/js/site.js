
function createOneRow(id,deviceId,status,level) {

    var row=$("<tr></tr>");

    row.append($("<td>"+id+"</td>"));

    row.append($("<td>"+deviceId+"</td>"));

    var state;

    if (status)
        state='ON';
    else
        state='OFF';

    /*var btn=$("<td><div class=\"switch switch-small\">" +
        "    <input type=\"checkbox\" checked />" +
        "</div></td>");*/
    var checkbox=$("<input type='checkbox'/>");

    if (status){
        checkbox.prop("checked","checked");
    }
    else
        checkbox.removeProp("checked");


    checkbox.prop("id",deviceId);

    checkbox.click(function () {
        console.log(this);
        if (this.checked) {
            $.get("ChangeSingleLightBeanServlet?lightPhoneId="+deviceId+"&luminance=2&state=true",function (data) {

            });
        }
        else {
            $.get("ChangeSingleLightBeanServlet?lightPhoneId="+deviceId+"&luminance=2&state=false",function (data) {

            });
        }

    });

    checkbox=$("<td>"+state+"</td>").append(checkbox);
    row.append(checkbox);

    //row.append(btn);
    var range=$("<input type='range' min='1' max='5' value='"+level+"'/>");

    range.on('input propertychange',function(){
        console.log(this.value);
        $.get("ChangeSingleLightBeanServlet?lightPhoneId="+deviceId+"&luminance="+this.value+"&state=true",function (data) {

        });
    });

    var t=$("<td></td>");
    t.append(range);
    row.append(t);

    return row;
}