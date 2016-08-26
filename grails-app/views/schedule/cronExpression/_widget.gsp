<input type="text" name="${property}" value="${value}">
<span class="expression">${net.redhogs.cronparser.CronExpressionDescriptor.getDescription(value)}</span>
<script>
    function updateExpression(event) {
        $(event.target).parent().children('.expression').text('...');
        console.log(event);
        $.ajax({
            url: '${g.createLink(controller: 'schedule', action: 'preview')}',
            data: {id: event.target.value},
            dataType: "json",
            complete: function(data) {
                if (event.target.value == data.responseJSON.expression) {
                    $(event.target).parent().children('.expression').text(data.responseJSON.description);
                }
            }
        });
    }
    $('input[name="${property}"]').on('change', updateExpression);
    $('input[name="${property}"]').on('keyup', updateExpression);
</script>
