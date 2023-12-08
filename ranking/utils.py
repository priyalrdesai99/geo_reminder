def custom_scoring_logic(score, task, user_preferences):
    """
    Adjusts the relevance score based on custom criteria.

    Args:
        score (float): The original relevance score from the ML model.
        task (str): The task description.
        user_preferences (dict): User preferences data.

    Returns:
        float: The adjusted relevance score.
    """

    # Example: Boost the score if the task matches a specific user preference
    if user_preferences.get('preferred_activity') in task:
        return score * 1.2  # Boosting the score by 20%

    return score


def log_ranking_results(ranked_results):
    """
    Logs the ranking results for analysis.

    Args:
        ranked_results (list): List of ranked tasks with scores.

    Returns:
        None
    """

    # Implement logging logic here
    # This could be writing to a file, printing to console, or sending to a logging service
    for result in ranked_results:
        print(f"Task: {result['Task']}, Score: {result['Relevance_Score']}")
