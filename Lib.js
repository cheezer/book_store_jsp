/**
 * Created by michaelxie on 14-12-24.
 */

function check_form(form_obj){
	alert(form_obj.searchAttribute.value+"='"+form_obj.attributeValue.value+"'");
    if( form_obj.attributeValue.value == ""){
        alert("Search field should be nonempty");
        return false;
    }
    return true;
}

function checkString(st)
{
    if (st.indexOf("'") != -1)
        return false;
    return true;
}

function checkInt(st)
{
    return /^\+?(0|[1-9]\d*)$/.test(st);
}

function checkDouble(st)
{
    return !isNaN(st) && (st != "");
}

function assTrue(flag, st)
{
    if (!flag)
    {
        alert(st);
        return false;
    }
    return true;
}
