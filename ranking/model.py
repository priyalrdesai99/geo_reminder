from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import pandas as pd

def rank_search_results(preprocessed_data):
    """
    Ranks search results based on their relevance to the search query and user preferences.

    Args:
        preprocessed_data (dict): A dictionary containing the preprocessed search query, tasks, and user preferences.

    Returns:
        DataFrame: A DataFrame with tasks and their relevance scores.
    """

    # Unpack the preprocessed data
    search_query, tasks, user_preferences = preprocessed_data['search_query'], preprocessed_data['tasks'], preprocessed_data['user_preferences']

    # Combine texts for TF-IDF vectorization
    combined_texts = [search_query, user_preferences] + tasks

    # TF-IDF Vectorization
    vectorizer = TfidfVectorizer()
    vectors = vectorizer.fit_transform(combined_texts)
    vectors = vectors.toarray()

    # Compute cosine similarity
    cosine_sim = cosine_similarity(vectors[0:1], vectors[2:])
    similarity_scores = cosine_sim[0]

    # Create a DataFrame to display tasks and their scores
    results_df = pd.DataFrame({
        'Task': tasks,
        'Relevance_Score': similarity_scores
    })

    # Sort the tasks based on their relevance score
    ranked_results = results_df.sort_values(by='Relevance_Score', ascending=False)

    return ranked_results.to_dict(orient='records')
