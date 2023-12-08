from flask import Flask, request, jsonify
from data_processing import preprocess_data
from model import rank_search_results

app = Flask(__name__)

@app.route('/rank', methods=['POST'])
def rank_tasks():
    data = request.json
    processed_data = preprocess_data(data)
    ranked_results = rank_search_results(processed_data)
    return jsonify(ranked_results)

if __name__ == '__main__':
    app.run(debug=True)
