from nltk.stem import WordNetLemmatizer
import nltk
nltk.download('wordnet')

def preprocess_data(data):
    """
    Preprocesses the input data for the machine learning model.

    Args:
        data (dict): A dictionary containing the search query, tasks, and user preferences.

    Returns:
        dict: A dictionary with preprocessed search query, tasks, and user preferences.
    """

    lemmatizer = WordNetLemmatizer()

    def preprocess_text(text):
        """
        Preprocesses a given text by lemmatizing and lowercasing.

        Args:
            text (str): The text to preprocess.

        Returns:
            str: The preprocessed text.
        """
        words = text.split()
        return ' '.join([lemmatizer.lemmatize(word.lower()) for word in words])

    # Preprocessing search query and user preferences
    preprocessed_query = preprocess_text(data['search_query'])
    preprocessed_preferences = ' '.join([preprocess_text(pref) for pref in data['user_preferences'].values()])

    # Preprocessing each task
    preprocessed_tasks = [preprocess_text(task) for task in data['tasks']]

    return {
        'search_query': preprocessed_query,
        'tasks': preprocessed_tasks,
        'user_preferences': preprocessed_preferences
    }
