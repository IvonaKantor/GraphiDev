using UnityEngine;
using UnityEngine.SceneManagement;

public class NewBehaviourScript : MonoBehaviour
{
    [SerializeField] KeyCode keyOne;
    [SerializeField] KeyCode keyTwo;
    [SerializeField] Vector3 moveDirection;

    private bool gameCompleted = false;

    private void FixedUpdate()
    {
        if (Input.GetKey(keyOne))
        {
            GetComponent<Rigidbody>().linearVelocity += moveDirection;
        }
        if (Input.GetKey(keyTwo))
        {
            GetComponent<Rigidbody>().linearVelocity -= moveDirection;
        }
        if (Input.GetKey(KeyCode.R))
        {
            SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
        }
        if (Input.GetKey(KeyCode.Q))
        {
            SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 1);
        }
    }

    private void OnTriggerEnter(Collider other)
    {
        if (this.CompareTag("Player") && other.CompareTag("Finish"))
        {
            if (SceneManager.GetActiveScene().buildIndex == 2)
            {
                gameCompleted = true;
                Time.timeScale = 0;
            }
            else
            {
                SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 1);
            }
        }

        if (this.CompareTag("Cube") && other.CompareTag("Cube"))
        {
            foreach (Activator button in FindObjectsOfType<Activator>())
            {
                button.canPush = false;
            }
        }
    }

    private void OnTriggerExit(Collider other)
    {
        if (this.CompareTag("Cube") && other.CompareTag("Cube"))
        {
            foreach (Activator button in FindObjectsOfType<Activator>())
            {
                button.canPush = true;
            }
        }
    }

    private void OnGUI()
    {
        if (gameCompleted)
        {
            GUIStyle style = new GUIStyle(GUI.skin.label);
            style.fontSize = 30;
            style.alignment = TextAnchor.MiddleCenter;
            style.normal.textColor = Color.yellow;

            GUI.Box(new Rect(Screen.width / 2 - 200, Screen.height / 2 - 100, 400, 200), "");

            GUI.Label(new Rect(Screen.width / 2 - 150, Screen.height / 2 - 50, 300, 100),
                     "Gratulacje!\nUkończyłeś grę!",
                     style);

            if (GUI.Button(new Rect(Screen.width / 2 - 50, Screen.height / 2 + 50, 100, 40), "Wyjdź"))
            {
                Application.Quit();
#if UNITY_EDITOR
                UnityEditor.EditorApplication.isPlaying = false;
#endif
            }
        }
    }
}