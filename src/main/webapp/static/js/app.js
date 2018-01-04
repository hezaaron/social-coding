/**
 * 
 */
var userChoices = {};
var questions = [{'id': 1, 'multiAnswer': false, 'problemDescription': 'Question?'}]
//var questions = {};

	$(document).ready(function() {
		$('next').click(handleNext);
		var q = $('#switchQuestion');
		$.each(questions, function() {
			q.append($('<option />').val(this.id).text(this.id));
		});
		
		handleCombo();
		q.change(handleCombo);
		
		function handleCombo() {
			var elem = $('#switchQuestion option:selected');
			var index = elem.index();
			$('#question').text(questions[index].problemDescription);
			loadChoices(questions[index].id, questions[index].multiAnswer);
		}
		
		function handleNext() {
			var choices = $('#choices').children('input');
			$.each(choices, function(i, elem) {
				userChoices[elem.id] = elem.checked;
			})
			
			
			
			var elem = $('#switchQuestion option:selected');
			var length = $('#switchQuestion').children('option').length;
			var index = elem.index;
			
			if(index == (length - 1)) {
				$('switchQuestion option:first-child').attr('selected', 'selected');
			}
			else{
				$('switchQuestion option:selected').next('option').attr('selected', 'selected');
			}
			
			$('switchQuestion').change();
		}
		
		function loadQuestions(id) {
			var question = 
			$.ajax({
				url: location.protocol + '//' + location.host + '/online-test-exam-maker' +'/question/' + id,
				dataType: 'json',
				type: 'GET',
				success: function (data, status){
					$.each(data, function(i, v){
						questions = [{'id': v.id, 'multiAnswer': v.multiAnswer, 'problemDescription': v.problemDescription}];
					})
				},
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