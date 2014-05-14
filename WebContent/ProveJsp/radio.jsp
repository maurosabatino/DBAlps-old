
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script  type="text/javascript">
function Disabilita(stato1,stato2,stato3,stato4,stato5,stato6){
document.getElementById('_1').disabled = stato1;
document.getElementById('_2').disabled = stato2;
document.getElementById('_3').disabled = stato3;
document.getElementById('_4').disabled = stato4;
document.getElementById('semplice').disabled = stato5;
document.getElementById('avanzata').disabled = stato6;
}
</script>
</head>

<body>
<form method="post" action="?save=1" enctype="multipart/form-data" name="modulo">
<div id="scelta_1_elenco_radio"><input type="radio" name="TipoInserimento" value="elenco" checked onClick="Disabilita(true, false, false, false, false, false);"/></div> 
<div id="scelta_2_elenco_radio"><input type="radio" name="TipoInserimento" value="semplice" onClick="Disabilita(false, false, false, false, true, false);"/></div>
<div id="scelta_3_elenco_radio"><input type="radio" name="TipoInserimento" value="avanzata" onClick="Disabilita(false, false, false, false, false, true);" /></div>
<div id="txt_titolo_elenco">ELENCO</div>
<div id="sei">1° :
<input name="_1" id="_1" type="text" disabled="disabled" size="70" /></div>
<div id="quattro">2° :
<input name="_2" id="_2" type="text" disabled="disabled" size="70" /></div>
<div id="due">3° :
<input name="_3 id="_3" type="text" disabled="disabled" size="70" /></div>
<div id="input_4">4° :
<input name="_4" id="_4" type="text" disabled="disabled" size="70" /></div> 
<div id="input_5">SEMPLICE
<textarea name="semplice" id="semplice" cols="66" rows="7" disabled="disabled" ></textarea></div>
<div id="input_6">AVANZATA
<textarea name="avanzata" id="avanzata" cols="66" rows="7" disabled="disabled" ></textarea></div>
</form>
</body>
