using UnityEngine;
using System.Collections.Generic;
using System.Collections;

public class Camera : MonoBehaviour
{
    [SerializeField] private Transform _car;

    private Vector3 _offset = new Vector3(4f, 7f, -15f);
    private float _speed = 10f;

    private void FixedUpdate()
    {
        var targetPosition = _car.TransformPoint(_offset);
        transform.position = Vector3.Lerp(transform.position, targetPosition, _speed * Time.deltaTime);

        var direction = _car.position - transform.position;
        var rotation = Quaternion.LookRotation(direction, Vector3.up);

        transform.rotation = Quaternion.Lerp(transform.rotation, rotation, _speed * Time.deltaTime);
    }
}
