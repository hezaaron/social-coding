/**
 * 
 */
$(document).ready(function() {
	
	var userChoices = {};
	var id = $('#examid').val();
	var qNum;
	
	loadQuestions(id, function(question){
		for(var i in question) {
			qNum = Number(i) + 1;
			$('#switchQuestion').append($('<option />').val(question[i].id).text(qNum));
		}
		qNum = 1;
		$('#question').text(qNum +'. '+ question[0].problemDescription);
		loadChoices(question[0].id, question[0].multiAnswer);
	});
	
	$('#go').click(handleCombo);
	$('#next').click(handleNext);

	function handleCombo() {
		var elem = $('#switchQuestion option:selected');
		var index = elem.index();
		qNum = Number(index) + 1;
		loadQuestions(id, function(question){
			$('#question').text(qNum +'. '+ question[index].problemDescription);
			loadChoices(question[index].id, question[index].multiAnswer);
		});
	}
	
	function handleNext() {
		var choices = $('#choices').children('input'),
		length = $('#switchQuestion').children('option').length,
		question = $('#question').text().slice(0,2);
		if(question >= 10){
			qNum = Number(question)
		}else{
			qNum = Number(question.charAt(0));
		}
		
		$.each(choices, function(i, elem) {
			userChoices[elem.id] = elem.checked;
		})
		
		if(qNum < length){
			var nextQuestion = qNum + 1;
			loadQuestions(id, function(question){
				$('#question').text(nextQuestion +'. '+ question[qNum].problemDescription);
				loadChoices(question[qNum].id, question[qNum].multiAnswer);
			});
		}
		
	}
	
	function loadQuestions(id, callback) {
		var questionData = new Array();
		$.ajax({
			url: location.protocol + '//' + location.host + '/online-test-exam-maker' +'/questions/' + id,
			dataType: 'json',
			type: 'GET',
			success: function (data, status){
				$.each(data, function(i, v){
					questionData.push({'id': v.id, 'multiAnswer': v.multiAnswer, 'problemDescription': v.problemDescription});
				});
				if(typeof callback === "function"){
					callback(questionData);
				}
			},
			error: function(){				
			}
		});
	}
	
	function loadChoices(id, isMulti) {
		var choices = $('#choices');
		choices.empty();
		$.ajax({
			url: location.protocol + '//' + location.host + '/online-test-exam-maker' +'/choices/' + id,
			dataType: 'json',
			type: 'GET',
			success: function (data, status) {
				var elemType = isMulti ? 'checkbox' : 'radio';
				$.each(data, function (i, v) {
					var btn = $('<input />', {type: elemType, name: 'choice', value: v.id, id: v.id});
					if(userChoices[v.id]){
						btn.attr('checked', true);
					}
					var label = $('<label />', {html: btn}).append(' ' + v.choiceText);
					choices.append(btn, [label, $('<br />'), $('<br />')]);
				})
			},
			error: function(){
			}
		});
	}
});