$(document).ready(function() {
    $('#password').on('input', function() {
        var password = $(this).val();
        var strength = 0;
        var tips = "";
        var passwordStrengthElement = $('#password-strength');

        // Check password length
        if(password.length == 0){
            passwordStrengthElement.addClass("d-none");
        }else{
            passwordStrengthElement.removeClass("d-none");
        }
        if (password.length < 8) {
            tips += "<br />- Nên tạo mật khẩu dài hơn.";
        } else {
            strength += 1;
        }

        // Check for mixed case
        if (password.match(/[a-z]/) && password.match(/[A-Z]/)) {
            strength += 1;
        } else {
            tips += "<br />- Mật khẩu nên sử dụng cả chữ thường và chữ hoa. ";
        }

        // Check for numbers
        if (password.match(/\d/)) {
            strength += 1;
        } else {
            tips += "<br />- Mật khẩu nên bao gồm ít nhất một chữ số. ";
        }

        // Check for special characters
        if (password.match(/[^a-zA-Z\d]/)) {
            strength += 1;
        } else {
            tips += "<br />- Mật khẩu nên bao gồm ít nhất một ký tự đặc biệt. ";
        }

        // Update the text and color based on the password strength
        if (strength < 2) {
            passwordStrengthElement.html("Độ mạnh: Dễ đoán. " + tips);
            passwordStrengthElement.css('color', 'red');
        } else if (strength === 2) {
            passwordStrengthElement.html("Độ mạnh: Trung bình. " + tips);
            passwordStrengthElement.css('color', 'orange');
        } else if (strength === 3) {
            passwordStrengthElement.html("Độ mạnh: Tốt. " + tips);
            passwordStrengthElement.css('color', 'blue');
        } else {
            passwordStrengthElement.html("Độ mạnh: Khó đoán. " + tips);
            passwordStrengthElement.css('color', 'green');
        }
    });
    
    let usernameTimeout = null;
    let emailTimeout = null;
    let phoneTimeout = null;
    
    const checkUsername = function() {
        var username = $('#username').val();
        if (username) {
            $.ajax({
                url: '/check-username',
                type: 'GET',
                data: { username: username },
                success: function(data) {
                    if (data) {
                        $('#username-error').text('username already exists').removeClass('d-none').addClass('d-block');
                        $('#info-button').prop('disabled', true);
                    } else {
                        $('#username-error').removeClass('d-block').addClass('d-none');
                        $('#info-button').prop('disabled', false);
                    }
                }
            });
        } else {
            $('#username-error').removeClass('d-block').addClass('d-none');
            $('#info-button').prop('disabled', false);
        }
    };
    
    const checkEmail = function() {
        var email = $('#email').val();
        if (email) {
            $.ajax({
                url: '/check-email',
                type: 'GET',
                data: { email: email },
                success: function(data) {
                    if (data) {
                        $('#email-error').text('Email already exists').removeClass('d-none').addClass('d-block');
                        $('#info-button').prop('disabled', true);
                    } else {
                        $('#email-error').removeClass('d-block').addClass('d-none');
                        $('#info-button').prop('disabled', false);
                    }
                }
            });
        } else {
            $('#email-error').removeClass('d-block').addClass('d-none');
            $('#info-button').prop('disabled', false);
        }
    };

    const checkPhone = function() {
        var phoneNumber = $('#phoneNumber').val();
        if (phoneNumber) {
            $.ajax({
                url: '/check-phone',
                type: 'GET',
                data: { phoneNumber: phoneNumber },
                success: function(data) {
                    if (data) {
                        $('#phone-error').text('Phone number already exists').removeClass('d-none').addClass('d-block');
                        $('#info-button').prop('disabled', true);
                    } else {
                        $('#phone-error').removeClass('d-block').addClass('d-none');
                        $('#info-button').prop('disabled', false);
                    }
                }
            });
        } else {
            $('#phone-error').removeClass('d-block').addClass('d-none');
            $('#info-button').prop('disabled', false);
        }
    };
    
    $('#username').on('input', function() {
        clearTimeout(usernameTimeout);
        usernameTimeout = setTimeout(checkUsername, 3000); // 3 giây
    });

    $('#email').on('input', function() {
        clearTimeout(emailTimeout);
        emailTimeout = setTimeout(checkEmail, 3000); // 3 giây
    });

    $('#phoneNumber').on('input', function() {
        clearTimeout(phoneTimeout);
        phoneTimeout = setTimeout(checkPhone, 3000); // 3 giây
    });
});