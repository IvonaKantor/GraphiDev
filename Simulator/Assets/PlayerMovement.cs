using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class PlayerMovement : MonoBehaviour
{

    public Rigidbody rb;
    public float speed;

    void Start()
    {
        
    }

    void Update()
    {
        
    }

    private void FixedUpdate() 
    {
        rb.linearVelocity = transform.forward * speed;
    }
}
