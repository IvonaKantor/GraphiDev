using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : MonoBehaviour
{

    public Rigidbody rb;
    public float speed;
    public float rotationSpeed;
    public float horizontalInput;

    void Start()
    {
        
    }

    void Update()
    {
        horizontalInput = Input.GetAxis("Horizontal");
        print(horizontalInput);
    }

    private void FixedUpdate() 
    {
        rb.velocity = transform.forward * speed;
        rb.angularVelocity = new Vector3(0, 3, 0);
    }
}
