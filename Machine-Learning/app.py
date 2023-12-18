from flask import Flask, request, jsonify
import numpy as np
import tensorflow as tf
from sklearn.preprocessing import StandardScaler

app = Flask(__name__)

# Load the model and scaler
model = tf.keras.models.load_model('predict_crime_model.h5')
scaler = StandardScaler()

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.get_json()

        # Preprocess input data
        input_data = np.array([[data['Jumlah Penduduk (jiwa)'], data['Jumlah Crime'], data['Crime per Capita']]])
        input_data_scaled = scaler.transform(input_data)

        # Make prediction
        prediction = model.predict(input_data_scaled)[0][0]

        return jsonify({'prediction': prediction})
    except Exception as e:
        return jsonify({'error': str(e)})

if __name__ == '__main__':
    # Run the Flask app on host '0.0.0.0' and port 5000
    app.run(host='0.0.0.0', port=5000)
