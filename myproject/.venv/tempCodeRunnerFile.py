@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        email = request.form.get('email')  # Use .get() to avoid KeyError
        password = request.form.get('password')

        if not email or not password:
            flash('Please enter both email and password.', 'danger')
            return render_template('login.html')

        # Validate user credentials
        cursor = mysql.connection.cursor()
        cursor.execute("SELECT password, is_verified, role FROM users WHERE email = %s", (email,))
        user = cursor.fetchone()
        cursor.close()

        if user:
            db_password, is_verified, role = user
            if not is_verified:
                flash('Email not verified. Please verify your email.', 'warning')
            elif check_password_hash(db_password, password):
                session['user'] = email
                session['role'] = role  # Store the role in the session
                flash('Login successful!', 'success')

                # Redirect to the appropriate dashboard based on the role
                if role == 'customer':
                    return redirect(url_for('customer_dashboard'))
                elif role == 'admin':
                    return redirect(url_for('admin_dashboard'))
                else:
                    flash('Role not recognized.', 'danger')
            else:
                flash('Invalid credentials. Please try again.', 'danger')
        else:
            flash('Email not registered.', 'danger')
    return render_template('login.html')